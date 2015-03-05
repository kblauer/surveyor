from django.shortcuts import render_to_response
from survey.models import Survey
from django.http import HttpResponseRedirect
from .forms import SurveyForm
from django.core.context_processors import csrf

def allSurveys(request):
    user = request.user
    username = user.username
    
    surveys = None
    
    try:
        surveys = Survey.objects.filter(user = user)
        
    except:
        surveys = None
    
    
    args = {}
    args['username'] = username
    args['surveys'] = surveys
    
    return render_to_response('allSurveys.html', args)

def getSurvey(request, survey_id=1):
    username = request.user.username
    
    survey = Survey.objects.get(id=survey_id)
    
    args = {}
    args['username'] = username
    args['survey'] = survey
    
    return render_to_response('getSurvey.html', args)

def createSurvey(request):
    if request.POST:
        form = SurveyForm(request.POST)
        if form.is_valid():
            survey = form.save(commit=False)
            survey.user = request.user
            survey.save()
            
            return HttpResponseRedirect('/survey/all/')
        
    else:
        form = SurveyForm()
    
    username = request.user.username
        
    args = {}
    args.update(csrf(request))
    
    args['form'] = form
    args['username'] = username
    
    return render_to_response('createSurvey.html', args)
