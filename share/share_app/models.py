from django.db import models
import json

# Create your models here.
class Category(models.Model):
  name = models.CharField(max_length = 64)
  description = models.TextField()

  def __str__(self):
    return json.dumps(self)

class Location(models.Model):
  city = models.CharField(max_length=12)
  post_code = models.SmallIntegerField()
  class Meta:
      unique_together = ('city', 'post_code',)
  def __str__(self):
    return json.dumps(self)

class Item(models.Model):
  description = models.CharField(max_length=164)
  header = models.CharField(max_length=32)
  location = models.ForeignKey(Location, on_delete=models.CASCADE)
  category = models.ForeignKey(Category, on_delete=models.CASCADE)

  def __str__(self):
    return json.dumps(self)
