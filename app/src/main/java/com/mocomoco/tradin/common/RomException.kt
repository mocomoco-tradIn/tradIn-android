package com.mocomoco.tradin.common

class InvalidTelException(override val message: String): Exception(message)
class NotMatchedAuthNumberException(override val message: String): Exception(message)
class DuplicateEmailException(override val message: String): Exception(message)
class DuplicateNicknameException(override val message: String): Exception(message)
class UnhandingException(override val message: String): Exception(message)
class ServerException(override val message: String): Exception(message)
