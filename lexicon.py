class Lexicon:
    def __init__(self):
        self.slots = 0
        self.table = []
        self.array = []
    

    def create(self, slots: int) -> None:
        self.slots = slots
        self.table = [None] * slots
        self.array = [" "] * (15 * slots)


    def insert(self, word: str) -> None:
        hash_val = self.__hash(word, 0)
        table_slot = self.__get_slot(hash_val)

        


    def delete(self, word: str) -> None:
        pass


    def search(self, word: str) -> None:
        pass


    def is_empty(self) -> bool:
        pass


    def is_full(self) -> bool:
        pass


    def print_lex(self) -> None:
        pass


    def __get_slot(self, start_idx: int) -> int:
        idx = start_idx

        while not self.table[idx]:
            idx += 1

            if idx >= len(self.table):
                self.__double_table_size()

        return idx


    def __double_table_size(self) -> None:
        self.table += [None] * len(self.table)


    def __hash(self, word: str, i: int) -> int:
        ascii_total = sum(ord(c) for c in word)

        return (((ascii_total - 4) % self.slots) + i ** 2) % self.slots