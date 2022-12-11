from django.shortcuts import render
from django.http import HttpResponse

from django.contrib.auth.models import User
from rest_framework import permissions
from rest_framework import viewsets
from rest_framework.response import Response
from rest_framework.views import APIView

from .models import Asset, AssetGroup
from .serializers import AssetSerializer, AssetGroupSerializer

class AssetViewSet(viewsets.ModelViewSet):
    queryset = Asset.objects.all()
    serializer_class = AssetSerializer
    # permission_classes = (permissions.IsAuthenticated,)

class AssetGroupViewSet(viewsets.ModelViewSet):
    queryset = AssetGroup.objects.all()
    serializer_class = AssetGroupSerializer
    # permission_classes = (permissions.IsAuthenticated,)
