from django.db import models

from django.contrib.auth.models import Group, User
# Create your models here.

class AssetGroup(models.Model):
    name = models.CharField(max_length=20)
    admin_group = models.ForeignKey(Group, on_delete=models.CASCADE, blank=True, null=True)
    open = models.BooleanField(default=True)
    price_rental = models.FloatField(default=0)
    price_usage = models.FloatField(default=0)
    price_time = models.FloatField(default=0)
    description = models.TextField(default="Description Text")
    form_template = models.TextField()

    def __str__(self):
        return f'{self.name} {self.admin_group.name}'


class Asset(models.Model):
    name = models.CharField(max_length=20)
    owner = models.ForeignKey(User, on_delete=models.CASCADE)
    asset_group = models.ForeignKey(AssetGroup, on_delete=models.CASCADE)
    price_rental = models.FloatField()
    price_usage = models.FloatField()
    price_time = models.FloatField()
    form_data = models.TextField()
    approved = models.BooleanField(default=False)

    def __str__(self):
        return f'{self.name} {self.owner}'