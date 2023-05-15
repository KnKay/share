from django.db import models
from django.contrib.auth.models import User
from django.db.models.signals import post_save
from django.dispatch import receiver


# Create your models here.
class Profile(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    street = models.CharField(max_length=20, default="backend creted user")
    city = models.CharField(max_length=20, default="backend creted user")
    post_code = models.IntegerField(default=0)
    user_pic = models.BinaryField(blank=True)
    def __str__(self) -> str:
        return "test"

@receiver(post_save, sender=User)
def create_user_profile(sender, instance, created, **kwargs):
    if created:
        Profile.objects.create(user=instance)

# @receiver(post_save, sender=User)
# def save_user_profile(sender, instance, **kwargs):
#     instance.profile.save()

class Registration(models.Model):
    email = models.EmailField(unique=True)
    username = models.CharField(max_length=20, unique=True)
    random = models.CharField(max_length=50, unique=True)
