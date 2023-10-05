from django.test import TestCase
from share.models import AssetGroup


class AnimalTestCase(TestCase):
    def setUp(self):
        AssetGroup.objects.create(name="lion")
        AssetGroup.objects.create(name="cat")

    def test_animals_can_speak(self):
        """Animals that can speak are correctly identified"""
        lion = AssetGroup.objects.get(name="lion")
        cat = AssetGroup.objects.get(name="cat")
        self.assertEqual(lion.name, 'lion')
