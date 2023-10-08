from django.test import TestCase
from share.models import AssetGroup
from rest_framework.test import APIRequestFactory
from share.views import AssetGroupViewSet
from rest_framework import status
from django.contrib.auth.models import User
from rest_framework.test import force_authenticate

class CategoryViewTestCase(TestCase):
    factory_anon = APIRequestFactory()

    def setUp(self):
        AssetGroup.objects.create(name="lion")
        User.objects.create(username="admin", is_superuser=True, is_staff=True)
        User.objects.create(username="user", is_superuser=False)


    def test_categories_created(self):
        lion = AssetGroup.objects.get(name="lion")
        self.assertEqual(lion.name, 'lion')
        all = AssetGroup.objects.all()
        self.assertEqual (len(all), 2 )

    def test_get(self):
        request = self.factory_anon.get("/api/v1/assetgroup")
        view = AssetGroupViewSet.as_view({'get':'list'})
        response = view(request)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_anonymous_post(self):
        request = self.factory_anon.post("/api/v1/assetgroup/")
        data = {'name': 'DabApps'}
        view = AssetGroupViewSet.as_view({'post':'create'})
        response = view(request,data, format='json')
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)

    def test_anonymous_put(self):
        request = self.factory_anon.post("/api/v1/assetgroup/1")
        data = {'name': 'DabApps2'}
        view = AssetGroupViewSet.as_view({'put':'update'})
        response = view(request,data, format='json')
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)

    def test_non_admin_post(self):
        user = User.objects.get(username='user')
        request = self.factory_anon.post("/api/v1/assetgroup/")
        data = {'name': 'DabApps'}
        force_authenticate(request, user=user)
        view = AssetGroupViewSet.as_view({'post':'create'})
        response = view(request,data, format='json')
        self.assertEqual(response.status_code, status.HTTP_403_FORBIDDEN)

    def test_non_admin_put(self):
        user = User.objects.get(username='user')
        request = self.factory_anon.post("/api/v1/assetgroup/")
        data = {'name': 'DabApps'}
        force_authenticate(request, user=user)
        view = AssetGroupViewSet.as_view({'put':'update'})
        response = view(request,data, format='json')
        self.assertEqual(response.status_code, status.HTTP_403_FORBIDDEN)

    def test_admin_post(self):
        user = User.objects.get(username='admin')
        request = self.factory_anon.post("/api/v1/assetgroup/",{'name': 'new idea'}, format='json')
        force_authenticate(request, user=user)
        view = AssetGroupViewSet.as_view({'post':'create'})
        response = view(request)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_admin_put(self):
        created = AssetGroup.objects.create(name="cat")
        user = User.objects.get(username='admin')
        request = self.factory_anon.put(f"/api/v1/assetgroup/{created.id}/",{'name': 'no_cat'}, format='json')
        force_authenticate(request, user=user)
        view = AssetGroupViewSet.as_view({'put':'update'})
        response = view(request, pk=created.id)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        updated = AssetGroup.objects.get(id=created.id)
        self.assertEqual(updated.name, "no_cat")

