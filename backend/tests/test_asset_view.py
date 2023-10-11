from django.test import TestCase
from share.models import AssetGroup, Asset
from rest_framework.test import APIRequestFactory
from share.views import AssetGroupViewSet
from rest_framework import status
from django.contrib.auth.models import User
from rest_framework.test import force_authenticate

class AssetViewTestCase(TestCase):
    factory_anon = APIRequestFactory()

    def setUp(self):
        AssetGroup.objects.create(name="lion")
        AssetGroup.objects.create(name="tiger")