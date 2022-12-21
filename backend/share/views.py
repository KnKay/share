from http import HTTPStatus


from rest_framework import permissions
from rest_framework import viewsets
from rest_framework.response import Response

from .models import Asset, AssetGroup, Transaction
from .serializers import AssetSerializer, AssetGroupSerializer, TransactionSerializer
from . import permissions as sharePermissions

class AssetViewSet(viewsets.ModelViewSet):
    queryset = Asset.objects.all()
    serializer_class = AssetSerializer
    permission_classes = (sharePermissions.IsAdminOrOwnerOrReadOnly,)

    @staticmethod
    def per_group(request):
        pass

class AssetGroupViewSet(viewsets.ModelViewSet):
    queryset = AssetGroup.objects.all()
    serializer_class = AssetGroupSerializer
    permission_classes = (sharePermissions.IsAdminOrReadOnly, )

class TransactionViewSet(viewsets.ModelViewSet):
    queryset =Transaction.objects.all()
    serializer_class = TransactionSerializer
    permission_classes = (sharePermissions.IsAdminOrReadOnly, )
    lookup_field = 'id'

    @staticmethod
    def list(request):
        id = request.user.id
        response = {}
        items = Transaction.objects.filter(owner=id)
        if items:
            response['count'] = len(items)
            response['results'] = []
            for item in items:
                response['results'].append(TransactionSerializer(item).data)
        return Response(response)