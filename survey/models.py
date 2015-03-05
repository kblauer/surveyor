from django.db import models
from django.contrib.auth.models import User

# Creates a model for the survey item.  It essentially
# creates the schema in the database for the Survey item,
# including all fields and types.  This represents the survey as a whole,
# and does not contain any information regarding questions or options.
class Survey(models.Model):
    # The user that created the survey
    user = models.OneToOneField(User)
    surveyName = models.CharField(max_length=50)
    surveyDescription = models.TextField()
    createTime = models.DateTimeField(auto_now_add=True)
    updateTime = models.DateTimeField(auto_now=True)

    def __unicode__(self):
        return self.surveyName