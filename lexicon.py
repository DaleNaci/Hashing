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
        hash_val = self.hash(word, 0)
        table_slot = self.get_slot(hash_val)
        array_idx = self.get_array_idx()

        self.table[table_slot] = array_idx
        self.add_to_array(word, array_idx)


    def delete(self, word: str) -> None:
        idx = "".join(self.array).index(word)
        
        for _ in range(len(word)):
            self.array[idx] = "*"
            idx += 1
        
        slot = self.hash(word, 0)
        self.table[slot] = None

        print(f"{word}\tdeleted from slot {slot}")


    def search(self, word: str) -> None:
        word_found = word in "".join(self.array)

        if word_found:
            slot = "".join(self.array).index(word)
            print(f"{word}\tfound at slot {slot}")
        else:
            print(f"{word}\tnot found")


    def is_empty(self) -> bool:
        for val in self.table:
            if val:
                return False
        
        return True


    def is_full(self) -> bool:
        for val in self.table:
            if not val:
                return False
        
        return True


    def print_lex(self) -> None:
        print("T:\n")
        self.print_table()

        print("\nA: ", end="")
        self.print_array()

        print()


    def print_table(self) -> None:
        for idx, val in enumerate(self.table):
            if val is None:
                print(f"{idx}:")
            else:
                print(f"{idx}: {val}")
        

    def print_array(self) -> None:
        for val in self.array:
            print(val, end="")


    def get_array_idx(self) -> int:
        idx = 0

        while self.array[idx] != " ":
            idx += 1   
        
        return idx


    def add_to_array(self, word, start_idx) -> None:
        idx = start_idx

        for c in word:
            self.array[idx] = c
            idx += 1
        
        self.array[idx] = "\\"


    def get_slot(self, start_idx: int) -> int:
        idx = start_idx

        while self.table[idx] is not None:
            idx += 1

            if idx >= len(self.table):
                self.double_table_size()

        return idx


    def double_table_size(self) -> None:
        self.slots *= 2
        self.table += [None] * len(self.table)
        self.array += [" "] * len(self.table)


    def hash(self, word: str, i: int) -> int:
        ascii_total = sum(ord(c) for c in word)

        return (((ascii_total - 4) % self.slots) + i ** 2) % self.slots