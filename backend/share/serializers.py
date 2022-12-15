
from rest_framework import serializers

from .models import Asset, AssetGroup, Transaction


class AssetSerializer(serializers.ModelSerializer):
    class Meta:
        model = Asset
        fields = ['id', 'name', 'owner', 'asset_group', 'pic']

class AssetGroupSerializer(serializers.ModelSerializer):
    class Meta:
        model = AssetGroup
        fields = ['id', 'name', 'open', 'admin_group', 'description', 'pic']

class TransactionSerializer(serializers.ModelSerializer):
    asset = AssetSerializer(read_only=True)
    class Meta:
        model = Transaction
        fields = ['id', 'asset', 'owner']
