from django.conf.urls import patterns, include, url

urlpatterns = patterns('',
                       
    url(r'^all/$', 'survey.views.allSurveys'),
    url(r'^get/(?P<survey_id>\d+)/$', 'survey.views.getSurvey'),
    url(r'^create/$', 'survey.views.createSurvey'),
    
    url(r'^edit/(?P<survey_id>\d+)/$', 'survey.views.editSurvey'),
    
    url(r'^addQuestion/$', 'question.views.addQuestion'),
                       
)