from django.shortcuts import render
from survey.models import Survey
from question.models import Question
from django.http.response import HttpResponseRedirect

# Create your views here.
def addQuestion(request):
    
    
    if request.POST:
        
        
        survey_id = request.POST['survey']
        try: 
            question = Question()
            survey = Survey.objects.get(id = survey_id)
            
            question.survey = survey
            question.questionName = request.POST['name']
            question.questionDescription = request.POST['description']
            question.questionType = request.POST['type']
            
            question.save()

            return HttpResponseRedirect('/survey/edit/' + str(survey_id))
        
        except:
            return HttpResponseRedirect('/survey/edit/' + str(survey_id))
        
    else:
        
        return HttpResponseRedirect('/')