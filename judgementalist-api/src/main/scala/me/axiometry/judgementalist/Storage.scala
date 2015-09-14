package me.axiometry.judgementalist

import akka.actor.Actor
import org.scala_tools.time.Imports._

trait Storage extends Actor

object Storage {
  sealed trait Request
  object Requests {
    object Contests {
      case class Get(id: String) extends Request
      case class List(sortBy: SortOrder = SortOrder.StartDate(),
                      offset: Int = 0,
                      count: Int = -1) extends Request
      case class Create(id: String,
                        name: String,
                        startDate: DateTime,
                        endDate: DateTime,
                        problemIds: Set[String]) extends Request
      case class AddParticipants(id: String, participantIds: String*) extends Request
      case class Delete(ids: String*) extends Request

      sealed trait SortOrder {
        def ascending: Boolean
        def andThen: Option[SortOrder]
      }
      object SortOrder {
        case class Id(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
        case class Name(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
        case class StartDate(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
        case class EndDate(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
      }
    }

    object Participants {
      case class Get(id: String) extends Request
      case class List(sortBy: SortOrder = SortOrder.Name(),
                      offset: Int = 0,
                      count: Int = -1) extends Request
      case class CreateUser(id: String,
                            name: String) extends Request
      case class CreateTeam(id: String,
                            name: String,
                            memberIds: Set[String]) extends Request
      case class Update(id: String, name: String) extends Request
      case class AddTeamMembers(id: String, memberIds: String*) extends Request
      case class RemoveTeamMembers(id: String, memberIds: String*) extends Request
      case class Delete(ids: String*) extends Request

      sealed trait SortOrder {
        def ascending: Boolean
        def andThen: Option[SortOrder]
      }
      object SortOrder {
        case class Id(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
        case class Name(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
      }
    }

    object Problems {
      case class Get(id: String) extends Request
      case class GetCases(id: String, sampleOnly: Boolean = false) extends Request
      case class List(sortBy: SortOrder = SortOrder.Name(),
                      offset: Int = 0,
                      count: Int = -1) extends Request
      case class Create(id: String,
                        name: String,
                        statement: String) extends Request
      case class AddCase(id: String,
                         index: Int,
                         input: String,
                         output: String,
                         timeLimit: Option[Long],
                         memoryLimit: Option[Long]) extends Request
      case class Update(id: String,
                        name: String = null,
                        statement: String = null) extends Request
      case class Delete(ids: String*) extends Request
      case class DeleteCases(id: String, indices: Int*) extends Request

      sealed trait SortOrder {
        def ascending: Boolean
        def andThen: Option[SortOrder]
      }
      object SortOrder {
        case class Id(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
        case class Name(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
        case class Difficulty(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
      }
    }

    object Submissions {
      case class Get(id: Long) extends Request
      case class GetState(id: Long) extends Request
      case class List(participantId: String = null,
                      problemId: String = null,
                      contestId: String = null,
                      sortBy: SortOrder = SortOrder.Date(),
                      offset: Int = 0,
                      count: Int = -1) extends Request
      case class Create(participantId: String,
                        problemId: String,
                        contestId: String = null,
                        source: String,
                        extension: String,
                        languageId: String) extends Request
      case class SetStateWaiting(id: Long) extends Request
      case class SetStateJudging(id: Long) extends Request
      case class SetStateCorrect(id: Long) extends Request
      case class SetStateIncorrect(id: Long, reason: String, output: Map[Int, String], diff: Map[Int, String]) extends Request
      case class Delete(ids: Long*) extends Request

      sealed trait SortOrder {
        def ascending: Boolean
        def andThen: Option[SortOrder]
      }
      object SortOrder {
        case class Id(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
        case class Date(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
        case class Problem(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
        case class Contest(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
        case class Participant(ascending: Boolean = false, andThen: Option[SortOrder] = None) extends SortOrder
      }
    }

    case class Search(query: String,
                      targets: Set[Search.Target] = Search.Target.values,
                      offset: Int = 0,
                      count: Int = -1) extends Request
    object Search {
      sealed trait Target
      object Target {
        case object Contests extends Target
        case object Participants extends Target
        case object Problems extends Target

        val values: Set[Target] = Set(Contests, Participants, Problems)
      }
    }
  }

  sealed trait Response
  object Responses {
    object Contests {
      case class Get(contest: Contest) extends Response
      case class GetNotFound(id: String) extends Response
      case class List(contests: Seq[Contest]) extends Response
      case class Create(contest: Contest) extends Response
      case class CreateFailure(reason: CreateFailure.Reason) extends Response
      object CreateFailure {
        sealed trait Reason
        object Reason {
          case class ConflictingId(id: String) extends Reason
          case class InvalidProblems(problemIds: String*) extends Reason
        }
      }
      case class AddParticipants(contest: Contest, participants: Participant*) extends Response
      case class Delete(ids: String*) extends Response
    }

    object Participants {
      case class Get(participant: Participant) extends Response
      case class GetNotFound(id: String) extends Response
      case class List(participants: Seq[Participant]) extends Response
      case class CreateUser(user: User) extends Response
      case class CreateTeam(team: Team) extends Response
      case class CreateFailure(reason: CreateFailure.Reason) extends Response
      object CreateFailure {
        sealed trait Reason
        object Reason {
          case class ConflictingId(id: String) extends Reason
          case class InvalidTeamMembers(memberIds: String*) extends Reason
        }
      }
      case class Update(participant: Participant) extends Response
      case class AddTeamMembers(team: Team, members: User*) extends Response
      case class RemoveTeamMembers(team: Team, members: User*) extends Response
      case class TeamMembersFailure(reason: TeamMembersFailure.Reason) extends Response
      object TeamMembersFailure {
        sealed trait Reason
        object Reason {
          case class InvalidTeamMembers(memberIds: String*) extends Reason
        }
      }
      case class Delete(ids: String*) extends Response
    }

    object Problems {
      case class Get(problem: Problem) extends Response
      case class GetNotFound(id: String) extends Response
      case class GetCases(problem: Problem, cases: Seq[ProblemCase]) extends Response
      case class GetCasesFailure(reason: GetCasesFailure.Reason) extends Response
      object GetCasesFailure {
        sealed trait Reason
        object Reason {
          case class InvalidProblem(id: String) extends Reason
        }
      }
      case class List(problems: Seq[Problem]) extends Response
      case class Create(problem: Problem) extends Response
      case class CreateFailure(reason: CreateFailure.Reason) extends Response
      object CreateFailure {
        sealed trait Reason
        object Reason {
          case class ConflictingId(id: String) extends Reason
        }
      }
      case class AddCase(problemCase: ProblemCase) extends Response
      case class AddCaseFailure(reason: AddCaseFailure.Reason) extends Response
      object AddCaseFailure {
        sealed trait Reason
        object Reason {
          case class InvalidProblem(id: String) extends Reason
          case class ConflictingIndex(index: Int) extends Reason
        }
      }
      case class Update(problem: Problem) extends Response
      case class Delete(ids: String*) extends Response
      case class DeleteCases(problem: Problem, indices: Int*) extends Response
      case class DeleteCasesFailure(reason: DeleteCasesFailure.Reason) extends Response
      object DeleteCasesFailure {
        sealed trait Reason
        object Reason {
          case class InvalidProblem(id: String) extends Reason
        }
      }
    }

    object Submissions {
      case class Get(submission: Submission) extends Response
      case class GetNotFound(id: Long) extends Response
      case class GetState(state: Submission.State) extends Response
      case class GetStateNotFound(id: Long) extends Response
      case class List(submissions: Seq[Submission]) extends Response
      case class Create(submission: Submission) extends Response
      case class CreateFailure(reason: CreateFailure.Reason) extends Response
      object CreateFailure {
        sealed trait Reason
        object Reason {
          case class InvalidParticipant(id: String) extends Reason
          case class InvalidProblem(id: String) extends Reason
          case class InvalidContest(id: String) extends Reason
          case class InvalidLanguage(id: String) extends Reason
        }
      }
      case class SetState(id: Long) extends Response
      case class SetStateFailure(reason: SetStateFailure.Reason) extends Response
      object SetStateFailure {
        sealed trait Reason
        object Reason {
          case class InvalidSubmission(id: Long) extends Reason
        }
      }
      case class Delete(ids: Long*) extends Response
    }

    case class Search(query: String,
                      contests: Seq[Contest],
                      participants: Seq[Participant],
                      problems: Seq[Problem]) extends Response
  }
}