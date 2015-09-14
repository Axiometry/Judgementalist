package me.axiometry.judgementalist.storage.sql

import me.axiometry.judgementalist.Storage
import me.axiometry.judgementalist.Storage.{ Request, Response }

class SqlStorage extends Storage {
  def receive = {
    case request: Request.Contest.Get =>
    case request: Request.Contest.List =>
    case request: Request.Contest.Create =>
    case request: Request.Contest.AddParticipants =>
    case request: Request.Contest.Delete =>

    case request: Request.Participant.Get =>
    case request: Request.Participant.List =>
    case request: Request.Participant.CreateUser =>
    case request: Request.Participant.CreateTeam =>
    case request: Request.Participant.AddTeamMembers =>
    case request: Request.Participant.RemoveTeamMembers =>
    case request: Request.Participant.Delete =>

    case request: Request.Problem.Get =>
    case request: Request.Problem.GetCases =>
    case request: Request.Problem.List =>
    case request: Request.Problem.Create =>
    case request: Request.Problem.Update =>
    case request: Request.Problem.AddCase =>
    case request: Request.Problem.DeleteCases =>
    case request: Request.Problem.Delete =>

    case request: Request.Submission.Get =>
    case request: Request.Submission.GetState =>
    case request: Request.Submission.List =>
    case request: Request.Submission.SetStateWaiting =>
    case request: Request.Submission.SetStateJudging =>
    case request: Request.Submission.SetStateCorrect =>
    case request: Request.Submission.SetStateIncorrect =>

    case request: Request =>
      // error
  }
}