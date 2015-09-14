package me.axiometry.judgementalist.storage.sql

import me.axiometry.judgementalist.Storage
import me.axiometry.judgementalist.Storage.{ Request, Response, Requests, Responses }

class SqlStorage extends Storage {
  def receive = {
    case request: Requests.Contests.Get =>
    case request: Requests.Contests.List =>
    case request: Requests.Contests.Create =>
    case request: Requests.Contests.AddParticipants =>
    case request: Requests.Contests.Delete =>

    case request: Requests.Participants.Get =>
    case request: Requests.Participants.List =>
    case request: Requests.Participants.CreateUser =>
    case request: Requests.Participants.CreateTeam =>
    case request: Requests.Participants.AddTeamMembers =>
    case request: Requests.Participants.RemoveTeamMembers =>
    case request: Requests.Participants.Delete =>

    case request: Requests.Problems.Get =>
    case request: Requests.Problems.GetCases =>
    case request: Requests.Problems.List =>
    case request: Requests.Problems.Create =>
    case request: Requests.Problems.Update =>
    case request: Requests.Problems.AddCase =>
    case request: Requests.Problems.DeleteCases =>
    case request: Requests.Problems.Delete =>

    case request: Requests.Submissions.Get =>
    case request: Requests.Submissions.GetState =>
    case request: Requests.Submissions.List =>
    case request: Requests.Submissions.SetStateWaiting =>
    case request: Requests.Submissions.SetStateJudging =>
    case request: Requests.Submissions.SetStateCorrect =>
    case request: Requests.Submissions.SetStateIncorrect =>

    case request: Request =>
      // error
  }
}