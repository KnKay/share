import pytest

from share.models import AssetGroup, Asset
from fixture import drf_client, admin_user
from share.views import AssetViewSet

from rest_framework.reverse import reverse
from rest_framework import status

from rest_framework.test import force_authenticate

@pytest.mark.django_db
def test_asset_creation(drf_client, admin_user):
    one = AssetGroup.objects.create(name="grp1")
    two = AssetGroup.objects.create(name="grp2")
    client = drf_client
    user = admin_user
    request = client.post("/api/v1/asset/", {'name': 'DabApps', 'asset_group' : '1'}, format='json')
    force_authenticate(request, user=user)
    view = AssetViewSet.as_view({'post':'create'})
    response = view(request)
    assert response.status_code == status.HTTP_201_CREATED
    found = Asset.objects.get(asset_group=1)
