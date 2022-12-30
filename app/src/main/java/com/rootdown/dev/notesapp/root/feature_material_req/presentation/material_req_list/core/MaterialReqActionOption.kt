package com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.core

enum class MaterialReqActionOption(val title: String) {
    EditReq("Edit Req"),
    DeleteReq("Delete Req");

    companion object {
        fun getByTitle(title: String): MaterialReqActionOption {
            values().forEach { action -> if (title == action.title) return action }
            return EditReq
        }

        fun getOptions(hasEditOption: Boolean): List<String> {
            val options = mutableListOf<String>()
            values().forEach { taskAction ->
                if (hasEditOption || taskAction != EditReq) {
                    options.add(taskAction.title)
                }
            }
            return options
        }
    }
}