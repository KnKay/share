from django.shortcuts import render
from django.http import HttpResponse

from django.contrib.auth.models import User
from rest_framework import permissions
from rest_framework import viewsets
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework import exceptions
from rest_framework.permissions import  IsAdminUser

from .models import Profile
from .serializers import ProfileSerializer

class UserViewSet(viewsets.ModelViewSet):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer
    permission_classes = (IsAdminUser ,)

class ProfileViewSet(viewsets.ModelViewSet):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer

    @staticmethod
    def me(request):
        id = request.user.id
        if id is None:
            raise exceptions.PermissionDenied()
        response = {}
        response = Profile.objects.get(user_id=id)
        return Response(ProfileSerializer(response).data)

    def register(request):
        pass
