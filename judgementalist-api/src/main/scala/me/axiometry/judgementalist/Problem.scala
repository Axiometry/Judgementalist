package me.axiometry.judgementalist

import java.io.Reader

case class Problem(id: String,
                   name: String,
                   statement: String,
                   difficulty: Option[Double])