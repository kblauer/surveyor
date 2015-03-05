from django.db import models
from django.contrib.auth.models import User

# Create your models here.
class UserProfile(models.Model):
    # creates a one-to-one link between the user and their UserProfile
    # in the database, this is implemented by putting a user field in the 
    # UserProfile, which holds the ID of the user it is associated with.
    user = models.OneToOneField(User)
    fullName = models.CharField(max_length=50)
    phone = models.CharField(max_length=15)
    numSurveys = models.IntegerField(default=0)
    
User.profile = property(lambda u: UserProfile.objects.get_or_create(user=u)[0])