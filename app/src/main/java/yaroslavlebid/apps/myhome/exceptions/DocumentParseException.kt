package yaroslavlebid.apps.myhome.exceptions

class DocumentParseException(doc: String) : Exception("Can't parse document: $doc")