import pytest

from fixture import drf_client, admin_user, std_user
from share.models import Asset, AssetGroup
from rest_framework.test import force_authenticate
from share.views import TransactionViewSet
from rest_framework import status
import datetime

@pytest.mark.django_db
def test_asset_creation(drf_client, std_user, admin_user):
    one = AssetGroup.objects.create(name="grp1")
    asset = Asset.objects.create(name="test_asset", asset_group=one, owner=admin_user)
    client = drf_client
    user = std_user
    now = datetime.datetime.now()
    start = now.isoformat()
    end = now + datetime.timedelta(days=1)
    end = end.isoformat()
    request = client.post("/api/v1/transaction/", {'date_from': start, 'date_to': end, 'asset' : asset.id}, format='json')
    force_authenticate(request, user=user)
    view = TransactionViewSet.as_view({'post':'create'})
    response = view(request)
    assert response.status_code == status.HTTP_201_CREATED
