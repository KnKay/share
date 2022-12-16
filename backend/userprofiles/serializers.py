from django.contrib.auth.models import User

from rest_framework import serializers

from .models import Profile

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['username']

class ProfileSerializer(serializers.ModelSerializer):
    user =  UserSerializer(read_only=True)
    class Meta:
        model = Profile
        fields = ['user', 'post_code', 'user_pic']
