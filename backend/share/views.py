from http import HTTPStatus


from rest_framework import permissions
from rest_framework import viewsets
from rest_framework.response import Response

from .models import Asset, AssetGroup
from .serializers import AssetSerializer, AssetGroupSerializer

class AssetViewSet(viewsets.ModelViewSet):
    queryset = Asset.objects.all()
    serializer_class = AssetSerializer
    permission_classes = (permissions.IsAuthenticatedOrReadOnly,)

class AssetGroupViewSet(viewsets.ModelViewSet):
    queryset = AssetGroup.objects.all()
    serializer_class = AssetGroupSerializer

    @staticmethod
    def post(request):
        if not request.user.is_staff:
            return Response(status=HTTPStatus.UNAUTHORIZED)
        item = AssetGroupSerializer(data=request.data)
        try:
            item.is_valid(raise_exception=True)
            item.save()
            return Response(status=HTTPStatus.CREATED)
        except ValueError as e:
            return (Response(data={'Exception': str(e)},
                                status=HTTPStatus.BAD_REQUEST))