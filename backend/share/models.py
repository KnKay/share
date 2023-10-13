'''
Models and signal handlers for the share app.
This will be the most important part.
'''
from django.db import models

from django.contrib.auth.models import  User
from django.db.models.signals import pre_save
from django.dispatch import receiver
from django.utils.translation import gettext_lazy as _

class AssetGroup(models.Model):
    '''
    Model for asset_group.
    '''
    name = models.CharField(max_length=20)
    open = models.BooleanField(default=True)
    price_rental = models.FloatField(default=0)
    price_usage = models.FloatField(default=0)
    description = models.TextField(default="Description Text")
    form_template = models.TextField()
    pic = models.BinaryField(blank=True)

    def __str__(self):
        return f'{self.name}'

class UserRole(models.Model):
    '''
    A user can have a role in an asset group.
    This will implement an RBAC. The Django user Group model seem not to fit
    so this should be an easy implementation
    '''
    user = models.ForeignKey(User, on_delete=models.CASCADE, blank=True, null=True, default=1)
    group = models.ForeignKey(AssetGroup, on_delete=models.CASCADE, blank=True, null=True, default=1)

    class Role(models.TextChoices):
        MEMBER = "M", _("Member")
        ADMIN = "A", _("Admin")

    role = models.CharField(
        max_length=1,
        choices=Role.choices,
        default=Role.MEMBER
    )

class Asset(models.Model):
    '''
    Our asset. This is what we can rent.
    '''
    name = models.CharField(max_length=20)
    description = models.TextField(default="")
    owner = models.ForeignKey(User, on_delete=models.CASCADE, blank=True, null=True, default=1)
    asset_group = models.ForeignKey(AssetGroup, on_delete=models.CASCADE, related_name="assets")
    price_rental = models.FloatField(default=0)
    price_usage = models.FloatField(default=0)
    price_time = models.FloatField(default=0)
    form_data = models.TextField(default="")
    approved = models.BooleanField(default=False)
    pic = models.BinaryField(blank=True)

    def __str__(self):
        return f'{self.name}'

class Transaction(models.Model):
    '''
    We want to make an appointment
    '''
    asset = models.ForeignKey(Asset, on_delete=models.CASCADE, related_name="transactions")
    date_from = models.DateTimeField()
    date_to = models.DateTimeField()
    billed = models.BooleanField(default=False)
    paid = models.BooleanField(default=False)
    accepted = models.BooleanField(default=False)
    beschwerde = models.TextField(blank=True, null=True)
    requester = models.ForeignKey(User, on_delete=models.CASCADE, blank=True, null=True, default=1, related_name="requester")


    def __str__(self) -> str:
        return f'{self.asset}, from {self.date_from}'

