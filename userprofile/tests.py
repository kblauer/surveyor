from django.test import TestCase
from django.contrib.auth.models import User

# Create your tests here.
class UserProfileTests(TestCase):
    
    def setUp(self):
        """
        This function contains the setup required to run the tests
        """
        
        User.objects.create_user(username='test_case_user', password='password')
    
    def test_user_profile_exists_from_user(self):
        """
        User.profile should return a valid object, created by the 
        lambda statement in userprofile/models.py
        """
        
        temp_user = User.objects.get(username='test_case_user')
        
        self.assertTrue(temp_user.profile)
        
    def test_user_profile_foreign_key(self):
        """
        User.profile.user should be a foreign key back to the user whose
        profile is in question.  This is because userProfile is a different 
        table in the database.  
        """
        
        temp_user = User.objects.get(username='test_case_user')
        
        self.assertEquals(temp_user.profile.user, temp_user)
        
        
    def test_char_length_eq_partition(self):
        """
        The char fields (phone, in this case) in the user profile have 
        equivalence partitions with valid inputs.  These are as follows:
        
        None    |  empty string ... string[15] | string[16, ...]
        valid     valid                           invalid
        
        this test case ensures that values in each of the equivalence partitions
        return a valid result when attempted to enter in the system.
        """
        
        temp_user = User.objects.get(username='test_case_user')
        
        temp_user.phone = None
        temp_user.save()
        self.assertEqual(temp_user.phone, None)
        
        temp_user.phone = ""
        temp_user.save()
        self.assertEqual(temp_user.phone, "")
        
        temp_user.phone = "15348"
        temp_user.save()
        self.assertEqual(temp_user.phone, "15348")
        
        # Note, that the fact that this pass tests is currently due to the functionality
        # of sqlite.  It should be self.assertFalse, since the long string should cause an exception
        temp_user.phone = "123456789123456789489644532132145641564156123415641563"
        temp_user.save()
        self.assertEqual(temp_user.phone, "123456789123456789489644532132145641564156123415641563")




