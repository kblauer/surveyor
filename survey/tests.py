from django.test import TestCase
from django.test import Client
from django.contrib.auth.models import User
from survey.models import Survey

# Create your tests here.
class SurveyObjectTests(TestCase):
    
    def setUp(self):
        """
        This function contains the setup required to run the tests
        """
        
        # create a test user
        self.user = User.objects.create_user(username='test_case_user', password='password')
        
        self.client = Client()
        
    def test_survey_creation_eq_partition(self):
        """
        This function tests inputs to the survey creation in terms of equivalence partitions.
        The following partitions are used to test valid and invalid inputs:
        
        None        |    Empty String   |       String          |     NotAString    
        invalid         valid                   valid                   invalid
        """
        user = User.objects.get(username='test_case_user')
        
        try:
            with transaction.atomic():
                survey = Survey.objects.create(user = user, surveyName = None, surveyDescription = None)
            # The statement above should fail, so if it doesn't we want to make an invalid assertion 
            # so that the test case will fail
            self.assertTrue(False)
        except:
            self.assertTrue(True)
            
        try:
            with transaction.atomic():
                survey = Survey.objects.create(user = user, surveyName = 123, surveyDescription = 123)
            # The statement above should fail, so if it doesn't we want to make an invalid assertion 
            # so that the test case will fail
            self.assertTrue(False)
        except:
            self.assertTrue(True)
        
        survey = Survey.objects.create(user = user, surveyName = '', surveyDescription = '')
        self.assertTrue(survey)
        
        survey = Survey.objects.create(user = user, surveyName = 'test', surveyDescription = 'test')
        self.assertTrue(survey)
        
    def test_survey_url_boundaries(self):
        """
        This tests the URL patterns defined in /survey/urls.py in order to ensure that each is
        followed exactly and there aren't any incorrect routings.  Boundary testing is used because 
        in this case we are looking at the boundaries between patterns and what the user can enter
        """
        # This URL is defined as an import in /surveyor/urls.py, however it should not actually return a page
        response = self.client.get('/survey/')
        self.assertEquals(response.status_code, 404)
        
        # This URL is for ths full list of surveys.  It exists as a boundary case that will work, so we test around the
        # boundary as well
        
        response = self.client.get('/survey/all/')
        self.assertEqual(response.status_code, 200)
        
        response = self.client.get('/survey/alls/')
        self.assertEqual(response.status_code, 404)
        
        # For the get URL, there should be an ID for which survey to get in the URL pattern.
        # Test the boundary of not having one, as well as having both a vailid and invalid entry
        response = self.client.get('/survey/get/')
        self.assertEqual(response.status_code, 404)
        
        Survey.objects.create(user = self.user, surveyName = 'test', surveyDescription = 'test')
        # this should retrieve the survey just created 
        response = self.client.get('/survey/get/1/')
        self.assertEqual(response.status_code, 200)
        
        response = self.client.get('/survey/get/-1/')
        self.assertEqual(response.status_code, 404)
        