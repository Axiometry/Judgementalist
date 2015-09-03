package me.axiometry.judgementalist

case class Team(id: String,
                name: String,
                members: Set[User]) extends Participant