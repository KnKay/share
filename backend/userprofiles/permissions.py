from share.permissions import IsAdminOrOwner
from rest_framework.permissions import SAFE_METHODS

class RegisterPerms(IsAdminOrOwner):
    def has_permission(self, request, view):
        is_admin = super().has_permission(request, view)
        return request.method in SAFE_METHODS or is_admin

    '''
    A method to give admins or owners write access.
    All other users can read
    '''
    def has_object_permission(self, request, view, obj):
        is_admin = bool(request.user and request.user.is_staff)
        is_owner = bool(request.user and request.user.id == obj.owner_id)
        is_safe  = request.method == 'GET'
        return bool(is_admin or is_owner or is_safe)