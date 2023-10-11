from http import HTTPStatus

from django.db.models import Prefetch

from rest_framework import permissions
from rest_framework import viewsets
from rest_framework.response import Response

from .models import Asset, AssetGroup, Transaction
from .serializers import AssetSerializer, AssetGroupSerializer, TransactionSerializer
from . import permissions as sharePermissions
from datetime import datetime, timedelta


class AssetViewSet(viewsets.ModelViewSet):
    queryset = Asset.objects.prefetch_related(Prefetch(
        'transactions', queryset=Transaction.objects.filter(
            accepted=True,
            date_from__gt=datetime.now(),
            date_from__lt=datetime.now()+timedelta(days=90)
            )
    ))
    serializer_class = AssetSerializer
    permission_classes = (sharePermissions.IsAdminOrOwnerOrReadOnly,)

    def create(self, request):
        request.data["owner"]=request.user.id
        return  super().create(request)

class AssetGroupViewSet(viewsets.ModelViewSet):
    queryset = AssetGroup.objects.all()
    queryset = AssetGroup.objects.prefetch_related(Prefetch(
        'assets'))
    serializer_class = AssetGroupSerializer
    permission_classes = (sharePermissions.IsAdminOrReadOnly, )

    @staticmethod
    def asset_list(request, id):
        response = []
        assets = Asset.objects.filter(asset_group=id)
        if assets:
            for item in assets:
                response.append(AssetSerializer(item).data)
        return Response(response)


class TransactionViewSet(viewsets.ModelViewSet):
    queryset = Transaction.objects.prefetch_related(Prefetch(
        'asset')).prefetch_related(Prefetch('requester'))
    serializer_class = TransactionSerializer
    permission_classes = (sharePermissions.IsRequestorOrOwner, )

    @staticmethod
    def list(request):
        id = request.user.id
        reply = []
        # What I requested
        requests = Transaction.objects.filter(requester=id)
        for request in requests:
            reply.append(TransactionSerializer(request).data)
        # What has been requested of my assets
        requests = Transaction.objects.filter(asset__owner=id)
        for request in requests:
            reply.append(TransactionSerializer(request).data)
        return Response(reply)
