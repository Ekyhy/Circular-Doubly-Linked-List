import threading
import time

class Node:
    def __init__(self, data):
        self.data = data
        self.next = None
        self.prev = None


class CircularDoublyLinkedList:
    def __init__(self):
        self.head = None
        self.size = 0
        self.isRunning = False

    # Tambah Berita
    def tambah_berita(self, teks):
        new_node = Node(teks)

        if self.head is None:
            self.head = new_node
            self.head.next = self.head
            self.head.prev = self.head
        else:
            tail = self.head.prev
            tail.next = new_node
            new_node.prev = tail
            new_node.next = self.head
            self.head.prev = new_node

        self.size += 1
        print(f"Data Berita: {new_node.data} berhasil ditambahkan!")

    # Stop running
    def stop_running(self):
        self.isRunning = False
        print("\n--- Tampilan Berita Berhenti ---")

    # Forward
    def forward_berita(self):
        if self.head is None:
            return

        self.isRunning = True
        curr = self.head

        while self.isRunning:
            print(f"<<< {curr.data} >>>")
            time.sleep(3)
            curr = curr.next

    # Backward
    def backward_berita(self):
        if self.head is None:
            return

        self.isRunning = True
        curr = self.head.prev

        while self.isRunning:
            print(f"<<< {curr.data} >>>")
            time.sleep(3)
            curr = curr.prev

    # Hapus
    def hapus_berita(self, pos):
        if self.head is None or pos < 1 or pos > self.size:
            print("Gagal Menghapus: posisi tidak valid")
            return

        curr = self.head
        for _ in range(1, pos):
            curr = curr.next

        if self.size == 1:
            self.head = None
        else:
            curr.prev.next = curr.next
            curr.next.prev = curr.prev

            if curr == self.head:
                self.head = curr.next

        self.size -= 1
        print(f"Data Berita: {curr.data} berhasil dihapus!")

    # Tampilkan berdasarkan posisi
    def tampilkan_berita(self, pos):
        if self.head is None:
            print("Berita kosong!")
            return

        if pos < 1 or pos > self.size:
            print("Nomor tidak ditemukan!")
            return

        curr = self.head
        for _ in range(1, pos):
            curr = curr.next

        print(f"Data: {curr.data}")


# ================= MAIN =================
def main():
    list_berita = CircularDoublyLinkedList()

    while True:
        print("\n==== Sistem Running Text Berita ====")
        print("1. Tambah Berita")
        print("2. Hapus Berita")
        print("3. Running Text Forward")
        print("4. Running Text Backward")
        print("5. Tampilkan Berita")
        print("6. Keluar")

        pilihan = int(input("Pilih: "))

        if pilihan == 1:
            teks = input("Masukkan berita: ")
            list_berita.tambah_berita(teks)

        elif pilihan == 2:
            pos = int(input("Masukkan nomor berita: "))
            list_berita.hapus_berita(pos)

        elif pilihan == 3:
            print("(Tekan ENTER untuk berhenti...)")

            t = threading.Thread(target=list_berita.forward_berita)
            t.start()

            input()  # tunggu ENTER
            list_berita.stop_running()
            t.join()

        elif pilihan == 4:
            print("(Tekan ENTER untuk berhenti...)")

            t = threading.Thread(target=list_berita.backward_berita)
            t.start()

            input()
            list_berita.stop_running()
            t.join()

        elif pilihan == 5:
            pos = int(input("Masukkan nomor berita: "))
            list_berita.tampilkan_berita(pos)

        elif pilihan == 6:
            print("Program selesai")
            break


if __name__ == "__main__":
    main()