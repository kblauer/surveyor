from django.test import TestCase
from django.test import Client
from django.contrib.auth.models import User
from django.core.urlresolvers import reverse

# Create your tests here.
class SurveyorRootTests(TestCase):
    
    def setUp(self):
        """
        This function contains the setup required to run the tests
        """
        
        # create a test user
        User.objects.create_user(username='test_case_user', password='password')
        
        self.client = Client()
        
        
    def test_index_page_get(self):
        response = self.client.get('/')
        
        self.assertEqual(response.status_code, 200, "Index Response status code")
        
    def test_absurd_url(self):
        response = self.client.get('/resfsd')
        
        self.assertEqual(response.status_code, 404, "Testing absurd URL")
        
    def test_index_context_boundaries(self):
        response = self.client.get('/')
        self.assertEqual(response.context['username'], None)
        
        login = self.client.login(username='test_case_user', password="password")
        self.assertTrue(login)
        
        response = self.client.get('/')        
        self.assertEqual(response.context['username'], 'test_case_user')
        
    
        
