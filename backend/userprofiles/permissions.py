from share.permissions import IsAdminOrOwner

class RegisterPerms(IsAdminOrOwner):
    def has_permission(self, request, view):
        is_admin = super().has_permission(request, view)
        is_safe  = request.method == 'POST'
        return is_safe or is_admin
