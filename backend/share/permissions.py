''''
Adopted permissions not available in default django rest
'''
from rest_framework.permissions import SAFE_METHODS, IsAuthenticatedOrReadOnly, IsAdminUser, IsAuthenticated

class IsAdminOrReadOnly(IsAdminUser):
    '''
    A class to allow only admins write but
    all users read access
    '''
    def has_permission(self, request, view):
        is_admin = super().has_permission(request, view)
        return request.method in SAFE_METHODS or is_admin

class IsAdminOrOwnerOrReadOnly(IsAuthenticatedOrReadOnly):
    '''
    A method to give admins or owners write access.
    All other users can read
    '''
    def has_object_permission(self, request, view, obj):
        is_admin = bool(request.user and request.user.is_staff)
        is_owner = bool(request.user and request.user.id == obj.owner_id)
        is_safe  = request.method in SAFE_METHODS
        is_user_post = bool(request.user and request.method == 'POST')
        return bool(is_admin or is_owner or is_safe or is_user_post)

class IsAdminOrOwner(IsAdminUser):
    '''
    A method to give admins or owners write access.
    All other users can read
    '''
    def has_object_permission(self, request, view, obj):
        is_admin = bool(request.user and request.user.is_staff)
        is_owner = bool(request.user and request.user.id == obj.owner_id)
        is_safe  = request.method in SAFE_METHODS
        is_user_post = bool(request.user and request.method == 'POST')
        return bool(is_admin or is_owner or is_safe or is_user_post)

class IsRequestorOrOwner(IsAuthenticated):
    '''
    A method to give admins or owners write access.
    All other users can read
    '''
    def has_object_permission(self, request, view, obj):
        is_admin =      bool(request.user and request.user.is_staff)
        is_owner =      bool(request.user and request.user.id == obj.asset.owner_id)
        is_requestor =  bool(request.user and request.user.id == obj.requester_id)
        is_user_post = bool(request.user and request.method == 'POST')
        return bool(is_admin or is_owner or is_user_post or is_requestor)
