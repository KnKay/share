from rest_framework.test import APIRequestFactory
from django.contrib.auth.models import User
import pytest


@pytest.fixture
def drf_client():
   return  APIRequestFactory()

@pytest.fixture
def admin_user():
   return  User.objects.create(username="admin", is_superuser=True, is_staff=True)
