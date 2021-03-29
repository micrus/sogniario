import 'package:frontend/models/completed_survey.dart';
import 'package:frontend/services/utils.dart';
import 'package:frontend/models/survey.dart';
import 'package:http/http.dart' as http;

import 'dart:convert';


class SurveyApi extends Utils {

  Future<Survey> getSurveys(String surveyFile) async {
    var response = await http.get(
        Uri.tryParse('${server}api/survey/$surveyFile'),
        headers: header(getToken()),
    );

    return Survey.fromJson(jsonDecode(response.body));
  }


  Future<bool> insertSurvey(CompletedSurvey completedSurvey) async {
    var response = await http.post(
        Uri.tryParse('${server}api/survey/createNew'),
        headers: header(getToken()),
        body: jsonEncode(completedSurvey.completedSurvey())
    );

    return response.statusCode == 200;
  }

}
