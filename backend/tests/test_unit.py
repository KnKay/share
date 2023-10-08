from django.test import TestCase
from share.models import AssetGroup
from rest_framework.test import APIRequestFactory
from share.views import AssetGroupViewSet
from rest_framework import status

class CategoryViewTestCase(TestCase):
    def setUp(self):
        AssetGroup.objects.create(name="lion")
        AssetGroup.objects.create(name="cat")

    def test_categories_created(self):
        lion = AssetGroup.objects.get(name="lion")
        self.assertEqual(lion.name, 'lion')
        all = AssetGroup.objects.all()
        self.assertEqual (len(all), 2 )

    def test_me(self):
        factory = APIRequestFactory()
        request = factory.get("/api/v1/assetgroup")
        view = AssetGroupViewSet.as_view({'get':'list'})
        response = view(request)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
