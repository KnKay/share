
from rest_framework import serializers
from datetime import datetime
from .models import Asset, AssetGroup, Transaction
from django.contrib.auth.models import User


class AssetTransactionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Transaction
        fields = ['accepted', 'date_from', 'date_to']

class AssetSerializer(serializers.ModelSerializer):
    transactions = AssetTransactionSerializer(read_only=True, many=True)
    class Meta:
        model = Asset
        fields = ['id', 'name', 'owner', 'asset_group', 'pic', 'description', 'transactions']

class AssetGroupSerializer(serializers.ModelSerializer):
    class Meta:
        model = AssetGroup
        fields = ['id', 'name', 'open', 'admin_group', 'description', 'pic']


class TransactionOwner(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ["username"]

class TransactionAssetSerializer(serializers.ModelSerializer):
    owner = TransactionOwner(read_only=True)
    class Meta:
        model = Asset
        fields = ['id', 'name', 'owner', 'asset_group', 'pic', 'description']

class TransactionRequester(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ["username"]

class TransactionSerializer(serializers.ModelSerializer):
    asset = TransactionAssetSerializer(read_only=True)
    requester = TransactionRequester(read_only=True)
    class Meta:
        model = Transaction
        fields = ['id', 'requester', 'date_from', 'date_to', 'asset', 'accepted']


