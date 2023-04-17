from file_parser import FileParser


def main():
    parser = FileParser(11)

    with open("file.txt", "r") as f:
        for line in f.readlines():
            parser.parse_line(line.strip())



if __name__ == "__main__":
    main()