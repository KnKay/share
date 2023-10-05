import pytest
from django.contrib.auth.models import User
'''
https://djangostars.com/blog/django-pytest-testing/

'''
@pytest.mark.django_db
def test_my_user():
    User.objects.create_user('me', 'lennon@thebeatles.com', 'johnpassword')
    me = User.objects.get(username='me').username
    assert (me is "me")
