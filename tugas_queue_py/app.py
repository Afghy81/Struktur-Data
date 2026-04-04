from flask import Flask, render_template, request, redirect, url_for
from antrian.priority_queue import QueueLinkedList
import os

app = Flask(__name__)

queue_normal = QueueLinkedList()
queue_priority = QueueLinkedList()

counter = 1

@app.route('/')
def index():
    return render_template(
        'index.html',
        normal=queue_normal.get_all(),
        priority=queue_priority.get_all(),
        total=queue_normal.size() + queue_priority.size(),
        called=None,
        empty=False
    )

@app.route('/enqueue', methods=['POST'])
def enqueue():
    global counter

    nama = request.form.get('nama', '').strip()

    if not nama:
        return redirect(url_for('index'))

    queue_normal.enqueue(counter, nama)
    counter += 1

    return redirect(url_for('index'))

@app.route('/enqueue_priority', methods=['POST'])
def enqueue_priority():
    global counter

    nama_depan = request.form.get('nama_depan', '').strip()
    nama_belakang = request.form.get('nama_belakang', '').strip()
    nik = request.form.get('nik', '').strip()

    if not nama_depan or not nama_belakang or not nik:
        return redirect(url_for('index'))

    nama = f"{nama_depan} {nama_belakang}"
    queue_priority.enqueue(counter, nama, 1)
    counter += 1

    return redirect(url_for('index'))

@app.route('/dequeue', methods=['POST'])
def dequeue():
    called_data = None
    empty = False

    # 🔥 AMBIL DATA (INI YANG SEBELUMNYA KAMU TIDAK PUNYA)
    if not queue_priority.isEmpty():
        data = queue_priority.dequeue()
    elif not queue_normal.isEmpty():
        data = queue_normal.dequeue()
    else:
        data = None

    # 🔥 HANDLE HASIL
    if data is None:
        empty = True
    else:
        called_data = {
            "nomor": data.nomor,
            "nama": data.nama
        }

    return render_template(
        'index.html',
        priority=queue_priority.get_all(),
        normal=queue_normal.get_all(),
        total=queue_priority.size() + queue_normal.size(),
        called=called_data,
        empty=empty
    )

if __name__ == '__main__':
    app.run(debug=True)