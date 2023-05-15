from http import HTTPStatus

from django.contrib.auth.models import User
from rest_framework import viewsets
from rest_framework.response import Response
from rest_framework import exceptions
from rest_framework.permissions import  IsAdminUser
from .permissions import RegisterPerms
from .models import Profile, Registration

from .serializers import ProfileSerializer, RegisterSerializer, RegistrationSerializer

import random, string, smtplib, ssl,os
from django.core.exceptions import ObjectDoesNotExist

class UserViewSet(viewsets.ModelViewSet):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer
    permission_classes = (IsAdminUser ,)

class ProfileViewSet(viewsets.ModelViewSet):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer
    permission_classes = (RegisterPerms ,)

    def retrieve(self, request, *args, **kwargs):
        id = request.user.id
        if id is None:
            raise exceptions.PermissionDenied()
        response = {}
        response = Profile.objects.get(user_id=id)
        return Response(ProfileSerializer(response).data)

    def create(self, request, *args, **kwargs):
        try:
            Registration.objects.get(random=request.data["random"])
        except ObjectDoesNotExist:
            return Response(status=HTTPStatus.BAD_REQUEST, data="Register unknown")

        profile = RegisterSerializer(data=request.data)
        try:
            profile.is_valid(raise_exception=True)
            id = profile.create(profile.data).id
            retval = Profile.objects.get(id=id)

            return Response(status=HTTPStatus.OK, data = ProfileSerializer(retval).data)
        except Exception as e:
            return (Response(data={'Exception': str(e)},
                                status=HTTPStatus.BAD_REQUEST))
        pass

class RegistrationiewSet(viewsets.ModelViewSet):
    queryset = Registration.objects.all()
    serializer_class = RegistrationSerializer
    # permission_classes = (RegisterPerms ,)

    @staticmethod
    def list(request):
        return Response(status=204)

    @staticmethod
    def retrieve(request, *args, **kwargs):
        id = kwargs.pop('pk')
        try:
            retval = Registration.objects.get(random=id)
            return Response(data=RegistrationSerializer(retval).data, status=HTTPStatus.OK)
        except Exception as e:
            error = str(e)
            if '' in error:
                return Response(status=404)
            return (Response(data={str(e)},
                        status=HTTPStatus.INTERNAL_SERVER_ERROR))

    def create(self, request, *args, **kwargs):
        # check if the mail is known...
        try:
            User.objects.get(email=request.data["email"]).count()
            User.objects.get(username=request.data["username"]).count()
        except ObjectDoesNotExist as e:
            # add a random string!
            letters = string.ascii_lowercase
            result_str = ''.join(random.choice(letters) for i in range(50))
            request.data["random"] = result_str
            resp = super().create(request, args, kwargs)
            if resp.status_code != 201:
                return resp
            #Send email
            context = ssl.create_default_context()
            with smtplib.SMTP_SSL(os.getenv('MAIL_SERVER'), os.getenv('MAIL_PORT'), context=context) as server:
                server.login(os.getenv('MAIL_USER'), os.getenv('MAIL_PASS'))
                message =  f"""Subject: Share Registration

                http://localhost:8000/api/v1/register/{result_str}"""
                server.sendmail(os.getenv('MAIL_USER'), request.data["email"],message)
            # Save the request
            return resp
        return Response(status=HTTPStatus.BAD_REQUEST)
