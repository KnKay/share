import pytest

from share.models import AssetGroup, Asset
from fixture import drf_client, admin_user, std_user
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

@pytest.mark.django_db
def test_asset_view_all(drf_client, admin_user):
    client = drf_client
    admin_user
    one = AssetGroup.objects.create(name="grp1")
    asset = Asset.objects.create(name="test_asset", asset_group=one, owner=admin_user)
    view = AssetViewSet.as_view({'get':'retrieve'})
    request = client.get("/api/v1/asset/")
    response = view(request, pk=asset.id)
    assert response.status_code == status.HTTP_200_OK

@pytest.mark.django_db
def test_asset_put_anon(drf_client, std_user):
    client = drf_client
    std_user
    one = AssetGroup.objects.create(name="grp1")
    asset = Asset.objects.create(name="test_asset", asset_group=one, owner=std_user)
    view = AssetViewSet.as_view({'put':'update'})
    request = client.put(f"/api/v1/asset/{asset.id}/",{'name': 'updated'}, format='json')
    response = view(request, pk=asset.id)
    assert response.status_code == status.HTTP_401_UNAUTHORIZED

@pytest.mark.django_db
def test_asset_put_admin(drf_client,std_user, admin_user):
    client = drf_client
    std_user
    admin_user
    one = AssetGroup.objects.create(name="grp1")
    asset = Asset.objects.create(name="test_asset", asset_group=one, owner=std_user)
    view = AssetViewSet.as_view({'put':'update'})
    request = client.put(f"/api/v1/asset/{asset.id}/",{'name': 'updated', 'asset_group':one.id}, format='json')
    force_authenticate(request, user=admin_user)
    response = view(request, pk=asset.id)
    assert response.status_code == status.HTTP_200_OK

@pytest.mark.django_db
def test_asset_view_owner(drf_client, std_user):
    client = drf_client
    user = std_user
    one = AssetGroup.objects.create(name="grp1")
    asset = Asset.objects.create(name="test_asset", asset_group=one, owner=std_user)
    view = AssetViewSet.as_view({'put':'update'})
    request = client.put(f"/api/v1/asset/{asset.id}/",{'name': 'updated', 'asset_group':one.id}, format='json')
    force_authenticate(request, user=user)
    response = view(request, pk=asset.id)
    assert response.status_code == status.HTTP_200_OK
