'''
Models and signal handlers for the share app.
This will be the most important part.
'''
from django.db import models

from django.contrib.auth.models import Group, User
from django.db.models.signals import pre_save
from django.dispatch import receiver

class AssetGroup(models.Model):
    '''
    Model for asset_group.
    '''
    name = models.CharField(max_length=20)
    admin_group = models.ForeignKey(Group, on_delete=models.CASCADE, blank=True, null=True)
    open = models.BooleanField(default=True)
    price_rental = models.FloatField(default=0)
    price_usage = models.FloatField(default=0)
    price_time = models.FloatField(default=0)
    description = models.TextField(default="Description Text")
    form_template = models.TextField()
    pic = models.BinaryField(blank=True)

    def __str__(self):
        return f'{self.name}'

class Asset(models.Model):
    '''
    Our asset. This is what we can rent.
    '''
    name = models.CharField(max_length=20)
    description = models.TextField(default="")
    owner = models.ForeignKey(User, on_delete=models.CASCADE, blank=True, null=True, default=1)
    asset_group = models.ForeignKey(AssetGroup, on_delete=models.CASCADE)
    price_rental = models.FloatField(default=0)
    price_usage = models.FloatField(default=0)
    price_time = models.FloatField(default=0)
    form_data = models.TextField(default="")
    approved = models.BooleanField(default=False)
    pic = models.BinaryField(blank=True)

    def __str__(self):
        return f'{self.name}'

@receiver(pre_save, sender=Asset)
def create_user_profile(sender, instance, **kwargs):
    '''
    This method will force the owner. This can be improved but is working for now.
    '''
    if instance.id is None:
        import inspect
        for frame_record in inspect.stack():
            if frame_record[3]=='get_response':
                request = frame_record[0].f_locals['request']
                break
        try:
            user_id = request.user.id
            user = User.objects.get(id=user_id)
            instance.owner=user
        except Exception as ex:
            raise ex

class Transaction(models.Model):
    '''
    We want to make an appointment
    '''
    asset = models.ForeignKey(Asset, on_delete=models.CASCADE)
    date_from = models.DateTimeField()
    date_to = models.DateTimeField()
    billed = models.BooleanField(default=False)
    paid = models.BooleanField(default=False)
    beschwerde = models.TextField(blank=True, null=True)
    owner = models.ForeignKey(User, on_delete=models.CASCADE, blank=True, null=True, default=1)

    def __str__(self) -> str:
        return f'{self.asset}, from {self.date_from}'

@receiver(pre_save, sender=Transaction)
def create_transaction_user(sender, instance, **kwargs):
    '''
    This method will force the owner. This can be improved but is working for now.
    '''
    if instance.id is None:
        import inspect
        for frame_record in inspect.stack():
            if frame_record[3]=='get_response':
                request = frame_record[0].f_locals['request']
                break
        try:
            user_id = request.user.id
            user = User.objects.get(id=user_id)
            instance.owner=user
        except Exception as ex:
            raise ex
