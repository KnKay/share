from http import HTTPStatus

from rest_framework import viewsets
from rest_framework.response import Response
from rest_framework import exceptions
from rest_framework.permissions import  IsAdminUser
from .permissions import RegisterPerms
from .models import Profile, Registration
from .serializers import ProfileSerializer, RegisterSerializer, RegistrationSerializer

class UserViewSet(viewsets.ModelViewSet):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer
    permission_classes = (IsAdminUser ,)

class ProfileViewSet(viewsets.ModelViewSet):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer
    permission_classes = (RegisterPerms ,)

    @staticmethod
    def me(request):
        id = request.user.id
        if id is None:
            raise exceptions.PermissionDenied()
        response = {}
        response = Profile.objects.get(user_id=id)
        return Response(ProfileSerializer(response).data)

    @staticmethod
    def register(request):
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
        #Base class method call
        #Send email
        return Response(status=200)
