from lexicon import Lexicon


class FileParser:
    def __init__(self):
        self.lex = Lexicon()
    

    def parse_line(self, line: str) -> None:
        command = int(line[:2])

        info = None

        if len(line) > 2:
            info = line[3:]

        if command == 10:
            self.lex.insert(info)

        elif command == 11:
            self.lex.delete(info)

        elif command == 12:
            self.lex.search(info)

        elif command == 13:
            self.lex.print_lex()

        elif command == 14:
            self.lex.create(int(info))

        elif command == 15:
            pass # Do nothing, 15 means it is a comment