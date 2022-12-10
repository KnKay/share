from django.db import models

from django.contrib.auth.models import Group, User
# Create your models here.

class AssetGroup(models.Model):
    name = models.CharField(max_length=20)
    admin_group = models.ForeignKey(Group, on_delete=models.CASCADE)
    open = models.BooleanField()
    price_rental = models.FloatField()
    price_usage = models.FloatField()
    price_time = models.FloatField()
    form_template = models.TextField()

class Asset(models.Model):
    name = models.CharField(max_length=20)
    owner = models.ForeignKey(User, on_delete=models.CASCADE)
    asset_group = models.ForeignKey(AssetGroup, on_delete=models.CASCADE)
    price_rental = models.FloatField()
    price_usage = models.FloatField()
    price_time = models.FloatField()
    form_data = models.TextField()