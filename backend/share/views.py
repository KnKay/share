from http import HTTPStatus


from rest_framework import permissions
from rest_framework import viewsets
from rest_framework.response import Response

from .models import Asset, AssetGroup
from .serializers import AssetSerializer, AssetGroupSerializer
from . import permissions as sharePermissions

class AssetViewSet(viewsets.ModelViewSet):
    queryset = Asset.objects.all()
    serializer_class = AssetSerializer
    permission_classes = (sharePermissions.IsAdminOrOwnerOrReadOnly,)

class AssetGroupViewSet(viewsets.ModelViewSet):
    queryset = AssetGroup.objects.all()
    serializer_class = AssetGroupSerializer
    permission_classes = (sharePermissions.IsAdminOrReadOnly, )

