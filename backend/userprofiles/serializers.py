from django.contrib.auth.models import User

from rest_framework import serializers

from .models import Profile
from .models import Registration

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['username']

class ProfileSerializer(serializers.ModelSerializer):
    user =  UserSerializer(read_only=True)
    class Meta:
        model = Profile
        fields = ['user']

class RegUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['username', 'password', 'email', 'id']

class RegisterSerializer(serializers.ModelSerializer):
    user =  RegUserSerializer(many=False)
    class Meta:
        model = Profile
        fields = ['user']

    def create(self, validated_data):
        user_json = validated_data.pop('user')
        user = RegUserSerializer(data=user_json)
        user.is_valid(raise_exception=True)
        user.save()
        user_added = User.objects.filter(email=user.data["email"])[0]
        return Profile.objects.filter(user_id=user_added.id)[0]

class RegistrationSerializer(serializers.ModelSerializer):
    class Meta:
        model = Registration
        fields = ['email', 'username', 'random']


from rest_framework_simplejwt.serializers import TokenObtainPairSerializer

class MyTokenObtainPairSerializer(TokenObtainPairSerializer):

    def validate(self, attrs):
        data = super().validate(attrs)
        data["access_token"] = data["access"]
        data["refresh_token"] = data["refresh"]
        return data
