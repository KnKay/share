from django.contrib.auth.models import User
from rest_framework import serializers

from .models import Asset, AssetGroup


class AssetSerializer(serializers.ModelSerializer):
    class Meta:
        model = Asset
        fields = ['name', 'owner', 'asset_group']

class AssetGroupSerializer(serializers.ModelSerializer):
    class Meta:
        model = AssetGroup
        fields = ['name', 'open', 'admin_group']

