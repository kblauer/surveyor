from django.conf.urls import patterns, include, url
from django.contrib import admin

urlpatterns = patterns('',
    
    # *** Homepage URL ***
    url(r'^edit/$', 'userprofile.views.user_profile', name='userProfile'),
    
)
