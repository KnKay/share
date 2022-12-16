"""backend URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include

from rest_framework import routers
from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView,
    TokenVerifyView,
)

from share import views as views
from userprofiles import views as profile_views

router = routers.SimpleRouter()
router.register(r'asset', views.AssetViewSet)
router.register(r'assetgroup', views.AssetGroupViewSet)
router.register(r'user', profile_views.UserViewSet)

urlpatterns = [
    path("admin/", admin.site.urls),

    path('api/v1/token/', TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('api/v1/token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('api/token/verify/', TokenVerifyView.as_view(), name='token_verify'),

    # Can we make this part more elegant using an overwrite? Which method must be overwritten?
    path(r'api/v1/transaction',views.TransactionViewSet.as_view({'post': 'create', 'get':'list'},)),
    path(r'api/v1/transaction/',views.TransactionViewSet.as_view({'post': 'create', 'get':'list'},)),
    path(r'api/v1/transaction/<int:id>',
        views.TransactionViewSet.as_view({'put': 'update', 'get':'retrieve'},)),

    path(r'api/v1/profile/', profile_views.ProfileViewSet.as_view({'get':'me'})),
    path('api/v1/', include(router.urls)),
]
