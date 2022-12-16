from django.contrib import admin

from .models import AssetGroup, Asset, Transaction
# Register your models here.

admin.site.register(Asset)
admin.site.register(AssetGroup)
admin.site.register(Transaction)
